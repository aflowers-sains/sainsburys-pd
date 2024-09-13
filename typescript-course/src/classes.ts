class Person {
  private name: string;
  private age: number;
  private hairColour: string;

  constructor(name: string, age: number, hairColour: string) {
    this.name = name;
    this.age = age;
    this.hairColour = hairColour;
  }

  public getIntroduction(): string {
    return `Hello, my name is ${this.name}`;
  }

  public getIntroductionTo(name: string): string {
    return `Hello ${name}, my name is ${this.name}`;
  }
}

let personByClass = new Person('name', 123, 'brown');
let personByClass2 = new Person('name2', 23, 'black');

console.log(personByClass.getIntroductionTo('Bob'));
console.log(personByClass2.getIntroductionTo('Bob'));

// interface to enforce contract without behaviour
interface NamedObject {
  name: string;
  getName(): string;
};

let dog: NamedObject = {
  name: 'dog',
  getName() {
    return this.name;
  }
}