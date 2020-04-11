package funsets

import org.junit._

/**
 * This class is a test suite for the methods in object FunSets.
 *
 * To run this test suite, start "sbt" then run the "test" command.
 */
class FunSetSuite {

  import FunSets._

  @Test def `contains is implemented`: Unit = {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)

    val s1to9: FunSet = (x: Int) => x > 0 && x < 10
    val s6to14: FunSet = (x: Int) => x > 5 && x < 15
    def allEven2to8: FunSet = (x: Int) => x > 0 && x < 10 && x % 2 == 0
  }

  /**
   * This test is currently disabled (by using @Ignore) because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", remvoe the
   * @Ignore annotation.
   * Add   @Ignore("not ready yet")  to ignore
   */
  @Test def `singleton set one contains one`: Unit = {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  @Test def `union contains all elements of each set`: Unit = {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  @Test def `intersects contains only common elements of two sets`: Unit = {
    new TestSets {
      val s = intersect(s1to9, s6to14)
      assert(contains(s, 6), "Intersect 6")
      assert(contains(s, 8), "Intersect 8")
      assert(contains(s, 9), "Intersect 9")
      assert(!contains(s, 10), "Intersect 10")
      assert(!contains(s, 5), "Intersect 5")
    }
  }

  @Test def `diff contains only elements only from first set that doesn't exists in second set`: Unit = {
    new TestSets {
      val s = diff(s1to9, s6to14)
      assert(contains(s, 5), "Diff 5")
      assert(!contains(s, 6), "Diff 6")
      assert(contains(s, 2), "Diff 2")
      assert(!contains(s, 10), "Diff 10")
    }
  }

  @Test def `filter contains only elements that satisfies the given predicate` : Unit = {
    new TestSets {
      val s = filter(s1to9, (x: Int) => x % 2 == 0)
      val list = for(i <- 2 until 10 by 2) yield i
      list.map( i => assert(contains(s, i)))
      assert(!contains(s, 1), "Filter only even")
      assert(!contains(s, 10), "filter less than 10")
    }
  }

  @Test def `forall returns true only for elements that satisfy the given predicate`: Unit = {
    new TestSets {
      val sLess10 = forall(s1to9, (x: Int) => x < 10)
      val sGreater8 = forall(s1to9, (x: Int) => x >
        8)
      assert(sLess10, "for all less than 10")
      assert(!sGreater8, "NOT all greater than 8")
    }
  }

  @Test def `exists returns true if all elements that satify the given predicate`: Unit = {
    new TestSets {
      assert(exists(s1to9, (x: Int) => x < 5), "exists less than 5")
      assert(!exists(s6to14, (x: Int) => x < 5), "not exists less than 5")
    }
  }

  @Test def `map returns transformed elements of the set`: Unit = {
    new TestSets {
      val transformedSet = List(1, 2, 3, 4)
      val actual:FunSet = map(allEven2to8, x => x/2)
      for(i <- transformedSet) {
        assert(contains(actual, i), s"${i*2} => $i")
      }
    }
  }

  @Rule def individualTestTimeout = new org.junit.rules.Timeout(10 * 1000)
}
