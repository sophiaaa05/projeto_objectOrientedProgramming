package xxl.core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import xxl.app.exception.InvalidCellRangeException;
import xxl.core.exception.ImportFileException;
import xxl.core.exception.InvalidSheetDimensionExeption;
import xxl.core.exception.MissingFileAssociationException;
import xxl.core.exception.UnavailableFileException;
import xxl.core.exception.UnrecognizedEntryException;


/********************************************************************************************
 * Class representing a spreadsheet application
 *********************************************************************************************/

public class Calculator {
  /** The current spreadsheet. */
  private Spreadsheet _activeSpreadsheet;
  private User _activeUser ;
  private List<User> _users;
  private String[] _nameFiles;
  private String _activeFile;

  // Constructor
  public Calculator() {

    String name = "root";

    _activeFile = null;
    _activeUser = new User(name);
    _users = new ArrayList<>();  // Initialize the list
    _users.add(_activeUser);
    _activeSpreadsheet = null;
  }

  // *** sets and gets *** //
  /**
   * Return the current spreadsheet.
   *
   * @returns the current spreadsheet of this application. This reference can be
   *          null.
   */

  public final Spreadsheet getSpreadsheet() {
    return _activeSpreadsheet;
  }

  /**
   * Return the current file.
   *
   * @returns the current file of this application. This reference can be null.
   */

  public final String getActiveFile() {
    return _activeFile;
  }

    /**
   * Set the user
   *
   * @returns the current file of this application. This reference can be null.
   */

  public void setUser(User user) { 
     _activeUser = user;
  }

  /**
   * Sets the name of the current file.
   */

  public void setActiveFile(String newNameFile) {
    _activeFile = newNameFile;
  }

  /**
   * Saves the serialized application's state into the file associated to the
   * current network.
   *
   * @throws FileNotFoundException           if for some reason the file cannot be
   *                                         created or opened.
   * @throws MissingFileAssociationException if the current network does not have
   *                                         a file.
   * @throws IOException                     if there is some error while
   *                                         serializing the state of the network
   *                                         to disk.
   */
  /*
   * Method that saves the spreadsheet and puts it in a file.
   */

  public void save() throws FileNotFoundException, IOException {
    try (ObjectOutputStream obOut = new ObjectOutputStream(new FileOutputStream(_activeSpreadsheet.getActiveFile()))) {
      // como a folha já foi guardada, o estado de _changed muda para false
      obOut.writeObject(_activeSpreadsheet);
      _activeSpreadsheet.setStateSheet(false);
      
    }
  }

  /**
   * Saves the serialized application's state into the specified file. The current
   * network is
   * associated to this file.
   *
   * @param filename the name of the file.
   * @throws FileNotFoundException           if for some reason the file cannot be
   *                                         created or opened.
   * @throws MissingFileAssociationException if the current network does not have
   *                                         a file.
   * @throws IOException                     if there is some error while
   *                                         serializing the state of the network
   *                                         to disk.
   */

  /*
   * Method to save the file name and sets it as active
   */

  public void saveAs(String filename) throws FileNotFoundException, IOException {

    _activeSpreadsheet.setActiveFile(filename);
    _activeFile = filename;
    save();
  }

  /**
   * @param filename name of the file containing the serialized application's
   *                 state
   *                 to load.
   * @throws UnavailableFileException if the specified file does not exist or
   *                                  there is
   *                                  an error while processing this file.
   */

  /*
   * Method that loads the file Name
   */

  public void load(String filename) throws UnavailableFileException, IOException, ClassNotFoundException {

    ObjectInputStream objIn = null;
    try {
      // Open a connection to the file

      objIn = new ObjectInputStream(new FileInputStream(filename));
      // Read the saved Spreadsheet object
      Spreadsheet sheet = (Spreadsheet) objIn.readObject();

      // deletes the old CutBuffer
      //_activeSpreadsheet.deleteCutBuffer();

      _activeFile = filename;
      _activeSpreadsheet = sheet;

    } finally { // To always close the file
      if (objIn != null)
        objIn.close();
    }
  }
  
  /**
   * Read text input file and create domain entities.
   *
   * @param filename name of the text input file
   * @throws ImportFileException
   * @throws InvalidCellRangeException
   */

  /*
   * Method to import a specific file
   */

  public void importFile(String filename) throws ImportFileException, InvalidCellRangeException {

    try {
      Parser parser = new Parser(_activeSpreadsheet);

      // fill in the new spreadsheet
      _activeSpreadsheet = parser.parseFile(filename);
      assert(_activeFile != null);

    } catch (IOException | UnrecognizedEntryException e) { 
      throw new ImportFileException(filename, e);
    }
  }

  /*
   * Method to create a spreadsheet
   */

  public void createSpreadsheet(int columns, int rows) throws InvalidSheetDimensionExeption { 
    if (rows <= 0 || columns <= 0)
      throw new InvalidSheetDimensionExeption(rows, columns);
    if(_activeSpreadsheet != null){
      // deletes the CutBuffer when it changes
      //_activeSpreadsheet.deleteCutBuffer();
    }
    // when a spreadsheet is created is always the active sheet
    _activeSpreadsheet = new Spreadsheet(rows, columns);
  }

}

