package xxl.app.edit;

import java.util.List;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.app.exception.InvalidCellRangeException;
import xxl.core.Cell;
import xxl.core.Spreadsheet;
import xxl.core.Range;

/**
 * Class for searching functions.
 */
class DoShow extends Command<Spreadsheet> {

  DoShow(Spreadsheet receiver) {
    super(Label.SHOW, receiver);
    addStringField("range", Message.address());
  }

  // if _cell.content == null return ""
  @Override
  protected final void execute() throws CommandException {
    String range = stringField("range");

    try {
      List<Cell> cells = _receiver.getCellsFromRange(range);
      
      System.out.println(cells.size());
      for (Cell cell : cells) {
        _display.addLine("" + cell + " " + cells.size());
      }
      _display.display();
    } catch (InvalidCellRangeException e) {
      throw new InvalidCellRangeException(range);
    }
  }
}