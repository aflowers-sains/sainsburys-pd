import { add } from './util';

let greeting: string = 'Hello from TypeScript!';
console.log(greeting);

console.log(`Sum of 1 and 2 is ${add(1, 2)}`);

function double(x: number): number {
  return x * 2;
}

function sayHello(): void {
  console.log('Hello!');
}

const triple = (x: number): number => x * 3;

let x: number = 100;

let quadruple: (x: number) => number;

quadruple = (x: number) => x * 4;

let names: string[] = ['a', 'b', 'c'];
let numbers: number[] = [1, 2, 3];

let typedNames: string[] = [];
