package xxl.app.edit;

import java.util.List;

//import javax.swing.text.html.HTMLEditorKit.Parser;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.app.exception.UnknownFunctionException;
import xxl.core.Cell;
import xxl.core.Spreadsheet;
import xxl.core.exception.UnrecognizedEntryException;
import xxl.core.Parser;
import xxl.core.Content;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for inserting data.
 */
class DoInsert extends Command<Spreadsheet> {

  DoInsert(Spreadsheet receiver) {
    super(Label.INSERT, receiver);
    addStringField("range", Message.address());
    addStringField("content", Message.contents());
  }

  @Override
  protected final void execute() throws CommandException {
    String range = stringField("range");
    String content = stringField("content");
    Parser parser = new Parser(_receiver);
    Content newContent;
    try {
      newContent = parser.parseContent(content);
      List<Cell> cells = _receiver.getCellsFromRange(range);
      for (Cell cell : cells) {
        cell.setContent(newContent);
      }
    } catch (UnrecognizedEntryException e) {
      Pattern pattern = Pattern.compile("=([^\\(]+)");
      Matcher matcher = pattern.matcher(content);
      if(matcher.find()){
        String extractedWord = matcher.group(1);
        throw new UnknownFunctionException(extractedWord);
      }
    }
  }
}
