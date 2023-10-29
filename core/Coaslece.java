package xxl.core;

import xxl.app.exception.InvalidCellRangeException;
import xxl.core.exception.UnrecognizedEntryException;
import java.util.List;

/********************************************************************************************
 * returns the first string that founds
 *********************************************************************************************/

public class Coaslece extends IntervalFunction {

    public Coaslece(String functionName, Range range1) {

        super(functionName, range1);
        try {
            startObserving(getInterval());
        } catch (InvalidCellRangeException e) {
            System.out.println("InvalidCellRangeException");
        }
    }

    @Override
    public Literal compute() throws UnrecognizedEntryException {

        List<Cell> range;

        try {

            range = getInterval().getRange();
            for (Cell cell : range) {
                if (cell.getContent().getValue() != null || cell.getContent() != null) {
                    try {
                        String value = cell.getContent().getValue().asString();
                        if(value != "'"){
                            return new LiteralString(value);
                        }
                    } catch (UnrecognizedEntryException e) {
                        System.out.println("UnrecognizedEntryException");
                    }
                }
            }
            return new LiteralString("'");

        } catch (InvalidCellRangeException e) {
            return null;
        }

    }

    public void accept(Visitor v) {
        v.visitCoaslece(this);
    }

}
