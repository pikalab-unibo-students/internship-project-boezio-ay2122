package clpqr.optimization

import clpqr.*
import org.junit.jupiter.api.Test

class SupTest: BaseTest() {

    @Test
    fun testSupVariable(){

        val theory = theoryParser.parseTheory(
            """
            problem(X, Y, Z) :- 
                { 2*X+Y =< 16, X+2*Y =< 11,
                  X+3*Y =< 15, Z = 30*X+50*Y
                }.
            """.trimIndent()
        )

        val goal = termParser.parseStruct(
            "problem(X,Y,Z),sup(Z,Sup)"
        )

        val solver = getSolver(theory)

        val solution = solver.solveOnce(goal)

        val sup = "309.9924428241242000"

        termParser.scope.with {
            solution.assertSolutionAssigns(
                 precision,
                varOf("Sup") to realOf(sup)
            )
        }
    }

    @Test
    fun testSupExpression(){

        val theory = theoryParser.parseTheory(
            """
            problem(X, Y, Z) :- 
                { 2*X+Y =< 16, X+2*Y =< 11,
                  X+3*Y =< 15
                }.
            """.trimIndent()
        )

        val goal = termParser.parseStruct(
            "problem(X,Y,Z),sup(30*X+50*Y,Sup)"
        )

        val solver = getSolver(theory)

        val solution = solver.solveOnce(goal)

        val sup = "309.9924428241242000"

        termParser.scope.with {
            solution.assertSolutionAssigns(
                precision,
                varOf("Sup") to realOf(sup)
            )
        }
    }
}