// a string literal
let x: 'Hello' | 'Goodbye' | 'Hi' = 'Hello';

x = 'Goodbye';

// does not work
// x = 'Hey';

// literal == a type of enum, only available at compile time as runtime is pure javascript

type ColumnLabel = '1' | '2' | '3';
type RowLabel = 'A' | 'B' | 'C';


type Move = `${RowLabel}${ColumnLabel}`;
let myMove: Move = 'A3';

// let invalidMove: Move = 'D4'; // not avialable

// pretend user input from a form field
let userRowInput = 'Hello!';
let userColumnInput = 'Goodbye!';
let userMoveInput = 'A3';

function attackSquare(row: RowLabel, col: ColumnLabel) {

}

function attackSquareByMove(move: Move) {
  console.log('move = ' + move.toString());
}

function isValidMove(str: string): str is Move {
  let [row, col] = str.split('');
  return isRowLabel(row) && isColumnLabel(col);
}

if(isValidMove(userMoveInput)) {
  attackSquareByMove(userMoveInput);
}

// this conditional is a combo - if it is included then is it s RowLabel compatible value
function isRowLabel(str: string): str is RowLabel {
  return ['A', 'B', 'C'].includes(str);
}

function isColumnLabel(str: string): str is ColumnLabel {
  return ['1', '2', '3'].includes(str);
}

if(isRowLabel(userRowInput) && isColumnLabel(userColumnInput)) {
  attackSquare(userRowInput, userColumnInput);
}