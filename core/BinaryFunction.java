package xxl.core;

import xxl.core.exception.UnrecognizedEntryException;

/*****************************************************************************************************
 * Class representing the BinaryFunctions, functions that use args of the type
 * integer or reference
 *****************************************************************************************************/

public abstract class BinaryFunction extends Function {

    private Content _arg0;
    private Content _arg1;

    public BinaryFunction(Content arg0, Content arg1) {
        this.setArg0(arg0);
        this.setArg1(arg1);
    }

    // *** gets and sets ***//
    public Content getArg0() {
        return _arg0;
    }

    public Content getArg1() {
        return _arg1;
    }

    public Literal getValue() {
        if (_arg0.getValue() == null && _arg1.getValue() == null) {
            return null;
        }
        try {
            return compute();
        } catch (UnrecognizedEntryException e) {
            return null;
        }
    }

    public void setArg0(Content arg0) {
        _arg0 = arg0;
    }

    public void setArg1(Content arg1) {
        _arg1 = arg1;
    }

    // *** abstract Methods ***//
    abstract Literal compute() throws UnrecognizedEntryException;

    public String toString() {
        return "=" + getName() + "(" + _arg0.toString().replace("=", "") + "," + _arg1.toString().replace("=", "")
                + ")";
    }

    public void accept(Visitor v) {
        v.visitBinaryFunction(this);
    }
}
