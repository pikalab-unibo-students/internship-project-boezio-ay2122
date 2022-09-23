package clpqr.mip

import clpqr.*
import it.unibo.tuprolog.core.Integer
import it.unibo.tuprolog.core.Real
import it.unibo.tuprolog.core.List as LogicList
import it.unibo.tuprolog.solve.Solver
import it.unibo.tuprolog.solve.flags.FlagStore
import it.unibo.tuprolog.solve.library.Libraries
import org.junit.jupiter.api.Test

class BBInfFiveTest: BaseTest() {

    @Test
    fun testBBInfVariable(){

        val goal = termParser.parseStruct(
            "{X >= Y+Z, Y >= 1, Z >= 1}, bb_inf([Y,Z],X,Inf,Vertex,0.0)."
        )

        val solver = Solver.prolog.solverWithDefaultBuiltins(
            otherLibraries = Libraries.of(ClpQRLibrary),
            flags = FlagStore.DEFAULT + (Precision to Real.of(precision)),
        )

        val solution = solver.solveOnce(goal)

        val xExpected = "2.0099999999999993"
        val elem = Real.of(1.0)
        val vertexExpected = LogicList.of(elem, elem)

        termParser.scope.with {
            solution.assertSolutionAssigns(
                varOf("Inf") to realOf(xExpected),
                varOf("Vertex") to vertexExpected
            )
        }
    }

}