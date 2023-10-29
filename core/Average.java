package xxl.core;

import xxl.app.exception.InvalidCellRangeException;
import xxl.core.exception.UnrecognizedEntryException;
import java.util.List;

/********************************************************************************************
 * receives a range, and returns the average of all the values possible asInt
 *********************************************************************************************/

public class Average extends IntervalFunction {

    public Average(String functionName, Range range1) {
        super(functionName, range1);
        try {
            startObserving(getInterval());
        } catch (InvalidCellRangeException e) {
            System.out.println("InvalidCellRangeException");
        }
    }

    @Override
    Literal compute() throws UnrecognizedEntryException {
        int value = 0;
        List<Cell> range;
        int size = 0;

        try {
            range = this.getInterval().getRange();
            for (Cell cell : range) {
                if (cell.getContent() != null) {
                    value += cell.getContent().getValue().asInt();
                    size++; 
                } else
                    return null;
            }

            int result = value / size;
            return new LiteralInteger(result);
        } catch (InvalidCellRangeException e) {
            return null;
        }
    }

    public void accept(Visitor v) {
        v.visitAverage(this);
    }
}
