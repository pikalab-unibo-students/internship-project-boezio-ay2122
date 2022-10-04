package clpfd.reification

import org.chocosolver.solver.constraints.nary.cnf.ILogical
import org.chocosolver.solver.constraints.nary.cnf.LogOp

object Xor : BinaryReificationOperator(" #\\") {
    override val operation: (ILogical, ILogical) -> LogOp = LogOp::xor
}