package clpfd

import clpCore.chocoModel
import it.unibo.tuprolog.core.Atom
import it.unibo.tuprolog.core.Integer
import it.unibo.tuprolog.core.Term
import it.unibo.tuprolog.core.Var
import it.unibo.tuprolog.solve.ExecutionContext
import it.unibo.tuprolog.solve.primitive.Solve
import org.chocosolver.solver.variables.IntVar
import org.chocosolver.solver.variables.Variable

val Solve.Request<ExecutionContext>.operatorsMap
    get(): Map<Atom, String> {
        return mapOf(
            Atom.of("#=") to "=",
            Atom.of("#\\=") to "!=",
            Atom.of("#>") to ">",
            Atom.of("#<") to "<",
            Atom.of("#>=") to ">=",
            Atom.of("#=<") to "<="
        )
    }

fun Solve.Request<ExecutionContext>.getIntAsVars(termList: List<Term>): List<IntVar>{
    val integerAsVars = mutableListOf<IntVar>()
    // Conversion of integers to int values
    for (elem in termList){
        if(elem is Integer){
            val intValue = elem.castToInteger().intValue.toInt()
            integerAsVars.add(chocoModel.intVar(intValue))
        }
    }
    return integerAsVars
}

fun Solve.Request<ExecutionContext>.getAsIntVar(term: Term, map: Map<Var, Variable>): IntVar {
    if(term is Var)
        return map[term.castToVar()] as IntVar
    else
        return chocoModel.intVar(term.castToInteger().value.toInt())
}