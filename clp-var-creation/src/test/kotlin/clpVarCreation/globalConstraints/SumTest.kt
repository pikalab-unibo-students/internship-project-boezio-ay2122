package clpVarCreation.globalConstraints

import clpVarCreation.ClpFdLibrary
import clpVarCreation.assertSolutionAssigns
import it.unibo.tuprolog.core.parsing.TermParser
import it.unibo.tuprolog.solve.Solver
import it.unibo.tuprolog.solve.library.Libraries
import it.unibo.tuprolog.theory.parsing.ClausesParser
import org.junit.jupiter.api.Test

class SumTest {

    private val termParser = TermParser.withDefaultOperators()
    private val theoryParser = ClausesParser.withDefaultOperators()

    @Test
    fun testSumEqualsInteger() {

        val theory = theoryParser.parseTheory(
            """
            problem(X, Y) :- 
                in(X, '..'(1, 10)), 
                in(Y, '..'(1, 10)), 
                sum([X,Y], #=, 10).
            """.trimIndent()
        )

        val goal = termParser.parseStruct(
            "problem(X,Y),label([X,Y])"
        )

        val solver = Solver.prolog.solverOf(
            staticKb = theory,
            libraries = Libraries.of(ClpFdLibrary)
        )

        val solution = solver.solveOnce(goal)

        termParser.scope.with {
            solution.assertSolutionAssigns(
                varOf("X") to intOf(1),
                varOf("Y") to intOf(9)
            )
        }
    }

    @Test
    fun testSumEqualsVariable() {

        val theory = theoryParser.parseTheory(
            """
            problem(X, Y, Z) :- 
                in(X, '..'(1, 10)), 
                in(Y, '..'(1, 10)),
                in(Z, '..'(1, 10)),
                sum([X,Y], #=, Z).
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
    fun testSumEqualsExpression() {

        val theory = theoryParser.parseTheory(
            """
            problem(X, Y, Z) :- 
                in(X, '..'(1, 10)), 
                in(Y, '..'(1, 10)),
                in(Z, '..'(1, 10)),
                sum([X,Y], #=, Z - 3).
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
                varOf("Z") to intOf(5)
            )
        }
    }

    @Test
    fun testSumNotEqualsInteger() {

        val theory = theoryParser.parseTheory(
            """
            problem(X, Y) :- 
                in(X, '..'(1, 10)), 
                in(Y, '..'(1, 10)),
                sum([X,Y], #\=, 2).
            """.trimIndent()
        )

        val goal = termParser.parseStruct(
            "problem(X,Y),label([X,Y])"
        )

        val solver = Solver.prolog.solverOf(
            staticKb = theory,
            libraries = Libraries.of(ClpFdLibrary)
        )

        val solution = solver.solveOnce(goal)

        termParser.scope.with {
            solution.assertSolutionAssigns(
                varOf("X") to intOf(1),
                varOf("Y") to intOf(2)
            )
        }
    }

    @Test
    fun testSumGreaterThanInteger() {

        val theory = theoryParser.parseTheory(
            """
            problem(X, Y) :- 
                in(X, '..'(1, 10)), 
                in(Y, '..'(1, 10)),
                sum([X,Y], #>, 2).
            """.trimIndent()
        )

        val goal = termParser.parseStruct(
            "problem(X,Y),label([X,Y])"
        )

        val solver = Solver.prolog.solverOf(
            staticKb = theory,
            libraries = Libraries.of(ClpFdLibrary)
        )

        val solution = solver.solveOnce(goal)

        termParser.scope.with {
            solution.assertSolutionAssigns(
                varOf("X") to intOf(1),
                varOf("Y") to intOf(2)
            )
        }
    }

    @Test
    fun testSumLessThanInteger() {

        val theory = theoryParser.parseTheory(
            """
            problem(X, Y) :- 
                in(X, '..'(1, 10)), 
                in(Y, '..'(1, 10)),
                sum([X,Y], #<, 3).
            """.trimIndent()
        )

        val goal = termParser.parseStruct(
            "problem(X,Y),label([X,Y])"
        )

        val solver = Solver.prolog.solverOf(
            staticKb = theory,
            libraries = Libraries.of(ClpFdLibrary)
        )

        val solution = solver.solveOnce(goal)

        termParser.scope.with {
            solution.assertSolutionAssigns(
                varOf("X") to intOf(1),
                varOf("Y") to intOf(1)
            )
        }
    }

    @Test
    fun testSumGreaterEqualsInteger() {

        val theory = theoryParser.parseTheory(
            """
            problem(X, Y) :- 
                in(X, '..'(1, 10)), 
                in(Y, '..'(1, 10)),
                sum([X,Y], #>=, 3).
            """.trimIndent()
        )

        val goal = termParser.parseStruct(
            "problem(X,Y),label([X,Y])"
        )

        val solver = Solver.prolog.solverOf(
            staticKb = theory,
            libraries = Libraries.of(ClpFdLibrary)
        )

        val solution = solver.solveOnce(goal)

        termParser.scope.with {
            solution.assertSolutionAssigns(
                varOf("X") to intOf(1),
                varOf("Y") to intOf(2)
            )
        }
    }

    @Test
    fun testSumLessEqualsInteger() {

        val theory = theoryParser.parseTheory(
            """
            problem(X, Y) :- 
                in(X, '..'(1, 10)), 
                in(Y, '..'(1, 10)),
                sum([X,Y], #=<, 3).
            """.trimIndent()
        )

        val goal = termParser.parseStruct(
            "problem(X,Y),label([X,Y])"
        )

        val solver = Solver.prolog.solverOf(
            staticKb = theory,
            libraries = Libraries.of(ClpFdLibrary)
        )

        val solution = solver.solveOnce(goal)

        termParser.scope.with {
            solution.assertSolutionAssigns(
                varOf("X") to intOf(1),
                varOf("Y") to intOf(1)
            )
        }
    }
}