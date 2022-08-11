package clpVarCreation.globalConstraints

import clpVarCreation.BaseTest
import clpVarCreation.ClpFdLibrary
import clpVarCreation.assertSolutionAssigns
import it.unibo.tuprolog.core.parsing.TermParser
import it.unibo.tuprolog.solve.Solver
import it.unibo.tuprolog.solve.library.Libraries
import it.unibo.tuprolog.theory.parsing.ClausesParser
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException

class GlobalCardinalityTwoTest: BaseTest() {

    @Test
    fun testGlobalCardinality() {

        val theory = theoryParser.parseTheory(
            """
            problem(X,Y,Z) :- 
                ins([X,Y,Z], '..'(1, 3)), 
                in(W, '..'(2, 2)),
                in(V, '..'(1, 1)),
                global_cardinality([X,Y,Z],['-'(1,W), '-'(2,V)]).
            """.trimIndent()
        )

        val goal = termParser.parseStruct(
            "problem(X,Y,Z),label([X,Y,Z])"
        )

        val solver = Solver.prolog.solverOf(
            staticKb = theory,
            libraries = Libraries.of(ClpFdLibrary)
        )

        val solution = solver.solveOnce(goal)

        termParser.scope.with {
            solution.assertSolutionAssigns(
                varOf("X") to intOf(1),
                varOf("Y") to intOf(1),
                varOf("Z") to intOf(2)
            )
        }
    }

    @Test
    fun testInvalidVs() {

        val theory = theoryParser.parseTheory(
            """
            problem(X,Y) :- 
                ins([X,Y], '..'(1, 3)), 
                in(W, '..'(2, 2)),
                in(V, '..'(1, 1)),
                global_cardinality([X,Y,1],['-'(1,W), '-'(2,V)]).
            """.trimIndent()
        )

        val goal = termParser.parseStruct(
            "problem(X,Y),label([X,Y])"
        )

        val solver = Solver.prolog.solverOf(
            staticKb = theory,
            libraries = Libraries.of(ClpFdLibrary)
        )

        assertThrows<IllegalArgumentException> {
            solver.solveOnce(goal)
        }
    }

    @Test
    fun testInvalidPairs() {

        val theory = theoryParser.parseTheory(
            """
            problem(X,Y,Z) :- 
                ins([X,Y,Z], '..'(1, 3)), 
                in(W, '..'(2, 2)),
                in(V, '..'(1, 1)),
                global_cardinality([X,Y,Z],['+'(1,W), '-'(2,V)]).
            """.trimIndent()
        )

        val goal = termParser.parseStruct(
            "problem(X,Y,Z),label([X,Y,Z])"
        )

        val solver = Solver.prolog.solverOf(
            staticKb = theory,
            libraries = Libraries.of(ClpFdLibrary)
        )

        assertThrows<IllegalArgumentException> {
            solver.solveOnce(goal)
        }
    }
}