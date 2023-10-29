package xxl.core;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import xxl.core.exception.UnrecognizedEntryException;

/********************************************************************************************
 * Class representing a cell. A 2d Array of celle makes a spreadsheet
 *********************************************************************************************/

public class Cell implements Serializable {
    @Serial
    private static final long serialVersionUID = 202356318359L;
    private int _column;
    private int _row;
    private Content _content;
    private List<Observer> _listObservers = new ArrayList<Observer>();

    // contructor
    public Cell(int column, int line) throws UnrecognizedEntryException {
        _column = column;
        _row = line;
        _content =  new LiteralNull();// in the begining every cells starts with the Content null
        _content.setMyCell(this);
    }

   

    // contructor 2 - does a deepCopy of the Content in the Cell to use in the
    // CutBuffer
    public Cell(Cell original, int line, int column) {
        this._content = original._content;
        this._column = column;
        this._row = line;
        if(this._content != null){
            _content.setMyCell(this);
        }
    }

    // *** gets and sets *** //
    public void setContent(Content c) throws UnrecognizedEntryException {
        _content = c;
        notifyObserver();
    }

    public Content getContent() {
        return _content;
    }

    public int getRow() {
        return _row;
    }

    public int getColumn() {
        return _column;
    }

    public Literal getValue() {
        if (_content == null)
            return null;
        return _content.getValue();
    }


    /*
     * Method to turn cell coordinates into a string
     */
    public String toString() {

        String s = (this._row + 1) + ";" + (this._column + 1) + "|"; // "x;y|"
        String signal = "=";

        if (this._content == null) // if the cells content is null, returns just "x;y|"
            return s;

        if (this.getContent().getValue() == null) // if the value of the content is null, returns "x;y| #VALUE"
            s += "#VALUE";

        if (this.getContent().toString().contains(signal)) {
            if (this.getValue() != null) {
                if(this.getValue().toString() == ""){
                    return s + "#VALUE" + this.getContent().toString();
                }
                return s + this.getValue().toString() + this.getContent().toString();
            }
        }
        return s + this.getContent().toString(); // if the value of the content isn´t null, returns "x;y| *value in
                                                 // string"
    }

    public void addObserver(Observer obs) {
        try {
            _listObservers.add(obs);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException");
        }

    }

    public void notifyObserver() throws UnrecognizedEntryException {
        for (Observer observer : _listObservers) {
            observer.update();
        }
    }
}
