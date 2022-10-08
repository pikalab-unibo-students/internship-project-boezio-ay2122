package clpfd.reflection

import clpCore.chocoModel
import clpCore.flip
import clpCore.variablesMap
import it.unibo.tuprolog.core.Integer
import it.unibo.tuprolog.core.Substitution
import it.unibo.tuprolog.core.Term
import it.unibo.tuprolog.core.Var
import it.unibo.tuprolog.solve.ExecutionContext
import it.unibo.tuprolog.solve.primitive.BinaryRelation
import it.unibo.tuprolog.solve.primitive.Solve
import org.chocosolver.solver.variables.IntVar

object FdDegree: BinaryRelation.NonBacktrackable<ExecutionContext>("fd_degree") {
    override fun Solve.Request<ExecutionContext>.computeOne(first: Term, second: Term): Solve.Response {
        ensuringArgumentIsVariable(0)
        val variable = first.castToVar()
        require(second.let { it is Var || it is Integer }){
            "$second is neither a variable nor an integer value"
        }
        val chocoModel = chocoModel
        val varsMap = chocoModel.variablesMap(listOf(variable), context.substitution).flip()
        return if(varsMap.let { it.isEmpty() || it[first] !is IntVar})
            replyFail()
        else{
            chocoModel.solver.propagate()
            val numConstraints = (varsMap[first] as IntVar).nbProps
            when(second){
                is Var -> replyWith(Substitution.of(second to Integer.of(numConstraints)))
                is Integer -> if(second.value.toInt() == numConstraints)
                    replySuccess()
                else
                    replyFail()
                else -> throw IllegalStateException()
            }
        }
    }
}