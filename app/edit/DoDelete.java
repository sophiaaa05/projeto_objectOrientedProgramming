package xxl.app.edit;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.core.Spreadsheet;

import xxl.core.exception.UnrecognizedEntryException;

/**************************************************************************************
 * clear
 **************************************************************************************/
class DoDelete extends Command<Spreadsheet> {

  DoDelete(Spreadsheet receiver) {
    super(Label.DELETE, receiver);
    addStringField("range", Message.address());
  }
  
  @Override
  protected final void execute() throws CommandException{
    String range = stringField("range");
    try {
      _receiver.clear(range);
    } catch (UnrecognizedEntryException e) {
    }
  }
}
