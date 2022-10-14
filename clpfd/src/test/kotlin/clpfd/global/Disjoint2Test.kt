package clpfd.global

import clpfd.BaseTest
import clpfd.assertSolutionAssigns
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException

class Disjoint2Test: BaseTest() {

    @Test
    fun testDisjoint2() {

        val theory = theoryParser.parseTheory(
            """
            problem(X1, Y1, X2, Y2) :- 
                ins([X1], 1),
                ins([X2, W1], 3),
                ins([W2], 2),
                ins([Y1, Y2], '..'(1, 10)),
                ins([H1,H2], '..'(1, 6)),
                disjoint2([f(X1,W1,Y1,H1),g(X2,W2,Y2,H2)]).
            """.trimIndent()
        )

        val goal = termParser.parseStruct(
            "problem(X1, Y1, X2, Y2),label([X1,Y1, X2, Y2])"
        )

        val solver = get_solver(theory)

        val solution = solver.solveOnce(goal)

        termParser.scope.with {
            solution.assertSolutionAssigns(
                varOf("X1") to intOf(1),
                varOf("Y1") to intOf(1),
                varOf("X2") to intOf(3),
                varOf("Y2") to intOf(2),
            )
        }
    }

    @Test
    fun testInvalidRectangleStruct() {

        val theory = theoryParser.parseTheory(
            """
            problem(X1, Y1, X2, Y2) :- 
                ins([X1], 1),
                ins([X2, W1], 3),
                ins([W2], 2),
                ins([Y1, Y2], '..'(1, 10)),
                ins([H2], '..'(1, 6)),
                disjoint2([f(X1,W1,Y1),g(X2,W2,Y2,H2)]).
            """.trimIndent()
        )

        val goal = termParser.parseStruct(
            "problem(X1, Y1, X2, Y2),label([X1,Y1, X2, Y2])"
        )

        val solver = get_solver(theory)

        assertThrows<IllegalArgumentException> {
            solver.solveOnce(goal)
        }
    }

    @Test
    fun testInvalidRectangleArgument() {

        val theory = theoryParser.parseTheory(
            """
            problem(X1, Y1, X2, Y2) :- 
                ins([X1], 1),
                ins([X2, W1], 3),
                ins([W2], 2),
                ins([Y1, Y2], '..'(1, 10)),
                ins([H2], '..'(1, 6)),
                disjoint2([f(a,W1,Y1),g(X2,W2,Y2,H2)]).
            """.trimIndent()
        )

        val goal = termParser.parseStruct(
            "problem(X1, Y1, X2, Y2),label([X1,Y1, X2, Y2])"
        )

        val solver = get_solver(theory)

        assertThrows<IllegalArgumentException> {
            solver.solveOnce(goal)
        }
    }
}