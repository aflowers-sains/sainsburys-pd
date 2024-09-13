class Queue<T> {
  private items: T[];

  // constructor() {
  //   this.items = [];
  // }

  constructor(startingValue: T) {
    this.items = [startingValue];
  }

  add(newItem: T) {
    this.items.push(newItem);
  }

  remove(): T | undefined {
    return this.items.shift();
  }
}

// let stringQueue = new Queue<string>();
let stringQueue = new Queue<string>('hello');

stringQueue.add('hello');
console.log(`First element is ${stringQueue.remove()}`);

// let numberQueue = new Queue<number>();
let numberQueue = new Queue<number>(2);

numberQueue.add(3);
console.log(`First element is ${numberQueue.remove()}`);

function printGeneric<T>(value: T): void {
  console.log(`The value is ${value}`);
}

printGeneric('Hello');