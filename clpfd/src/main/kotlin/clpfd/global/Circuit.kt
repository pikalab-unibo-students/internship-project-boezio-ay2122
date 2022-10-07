package clpfd.global


import clpCore.chocoModel
import clpCore.flip
import clpCore.setChocoModel
import clpCore.variablesMap
import clpfd.getIntAsVars
import it.unibo.tuprolog.core.Term
import it.unibo.tuprolog.core.Var
import it.unibo.tuprolog.solve.ExecutionContext
import it.unibo.tuprolog.solve.primitive.Solve
import it.unibo.tuprolog.solve.primitive.UnaryPredicate
import org.chocosolver.solver.variables.IntVar

object Circuit: UnaryPredicate.NonBacktrackable<ExecutionContext>("circuit") {
    override fun Solve.Request<ExecutionContext>.computeOne(first: Term): Solve.Response {
        ensuringArgumentIsList(0)
        val circuit = first.castToList().toList()
        val circuitVars = circuit.filterIsInstance<Var>().distinct()
        val chocoModel = chocoModel
        val varsMap = chocoModel.variablesMap(circuitVars, context.substitution).flip()
        val intAsVars = getIntAsVars(circuit)
        val chocoCircuit = circuitVars.map { varsMap[it] as IntVar}.toMutableList()
        chocoCircuit.addAll(intAsVars)
        val allVars = chocoCircuit.toList().toTypedArray()
        chocoModel.circuit(allVars).post()
        return replySuccess {
            setChocoModel(chocoModel)
        }
    }
}