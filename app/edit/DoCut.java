package xxl.app.edit;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.core.Spreadsheet;

import xxl.core.exception.UnrecognizedEntryException;

/**************************************************************************************
 * copy + clear
 **************************************************************************************/
class DoCut extends Command<Spreadsheet> {

  DoCut(Spreadsheet receiver) {
    super(Label.CUT, receiver);
    addStringField("range", Message.address());
  }
  
  @Override
  protected final void execute() throws CommandException {
    String range = stringField("range");
    _receiver.copy(range);
    try {
      _receiver.clear(range);
    } catch (UnrecognizedEntryException e) {
    }
    
  }
}
