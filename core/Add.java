package xxl.core;

import xxl.core.exception.UnrecognizedEntryException;

/********************************************************************************************
 * Class representing a addition operation with two arguments of the type
 * Reference or Int
 *********************************************************************************************/

public class Add extends BinaryFunction {

    public Add(Content arg0, Content arg1) {
        super(arg0, arg1);
        setName("ADD");

    }

    @Override
    protected Literal compute() throws UnrecognizedEntryException {

        try {
            int arg0 = this.getArg0().getValue().asInt();
            int arg1 = this.getArg1().getValue().asInt();
            return new LiteralInteger(arg0 + arg1);
        } catch (NullPointerException e) {
            return null;
        }

    }

    public void accept(Visitor v) {
        v.visitAdd(this);
    }
}
