package xxl.app.edit;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.core.Spreadsheet;

/**************************************************************************************
 * cutbuffer [i] = getCellsforRange.content[i]
 **************************************************************************************/
class DoPaste extends Command<Spreadsheet> {

  DoPaste(Spreadsheet receiver) {
    super(Label.PASTE, receiver);
    addStringField("range", Message.address());
  }

  @Override
  protected final void execute() throws CommandException {
    String range = stringField("range");
    _receiver.paste(range); 
  }
}