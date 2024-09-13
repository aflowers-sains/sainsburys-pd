let xx: number = 10;
let pi: number = 3.142;

let isConnected: boolean = false;

let person: object = {
  name: 'name',
  hairColour: 'black',
};

let genericNumbers: Array<number> = [1, 2, 3];

// not great - class is better
let personTuple: [string, number, boolean] = ['name', 42, true];

// skip any type in general
let something: any = 'Hello!';
something = 42;

// skip any array - use union
let data: any[] = ['hello', 1, true, []];

let nothing: undefined = undefined;
let nullNothing: null = null;
