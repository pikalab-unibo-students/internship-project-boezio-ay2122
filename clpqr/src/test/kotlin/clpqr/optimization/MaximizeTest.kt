package clpqr.optimization

import clpqr.*
import it.unibo.tuprolog.core.Real
import it.unibo.tuprolog.solve.Solver
import it.unibo.tuprolog.solve.flags.FlagStore
import  it.unibo.tuprolog.solve.library.toRuntime
import org.junit.jupiter.api.Test
import kotlin.test.Ignore

class MaximizeTest: BaseTest() {

    @Test
    fun testMaximizeVariable(){

        val theory = theoryParser.parseTheory(
            """
            problem(X, Y, Z) :- 
                { 2*X+Y =< 16, X+2*Y =< 11,
                  X+3*Y =< 15, Z = 30*X+50*Y
                }.
            """.trimIndent()
        )

        val goal = termParser.parseStruct(
            "problem(X,Y,Z),maximize(Z)"
        )

        val solver = Solver.prolog.solverWithDefaultBuiltins(
            otherLibraries = ClpQRLibrary.toRuntime(),
            flags = FlagStore.DEFAULT + (Precision to Real.of(precision)),
            staticKb = theory
        )

        val solution = solver.solveOnce(goal)

        val xExpected = "7.0001079596553750"
        val yExpected = "2.0007557175876030"
        val zExpected = "309.9924428241242000"

        termParser.scope.with {
            solution.assertSolutionAssigns(
                precision,
                varOf("X") to realOf(xExpected),
                varOf("Y") to realOf(yExpected),
                varOf("Z") to realOf(zExpected)
            )
        }
    }

    @Test @Ignore
    fun testMaximizeVariableGoal(){

        val goal = termParser.parseStruct(
            "{ 2*X+Y =< 16, X+2*Y =< 11, X+3*Y =< 15, Z = 30*X+50*Y }, maximize(Z)"
        )

        val solver = Solver.prolog.solverWithDefaultBuiltins(
            otherLibraries = ClpQRLibrary.toRuntime(),
            flags = FlagStore.DEFAULT + (Precision to Real.of(precision))
        )

        val solution = solver.solveOnce(goal)

        val xExpected = "7.0001079596553750"
        val yExpected = "2.0007557175876030"
        val zExpected = "309.9924428241242000"

        termParser.scope.with {
            solution.assertSolutionAssigns(
                precision,
                varOf("X") to realOf(xExpected),
                varOf("Y") to realOf(yExpected),
                varOf("Z") to realOf(zExpected)
            )
        }
    }
}