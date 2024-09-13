
let personWithoutClass: { name:string, hairColour: string, age:number } = {
  name: 'name',
  hairColour: 'black',
  age: 123,
};

type PersonType = { name:string, hairColour: string, age:number };

let personTyped: PersonType = {
  name: 'name',
  hairColour: 'black',
  age: 123,
};

// ? adds in | undefined
type PersonTypeOptional = { name:string, hairColour?: string, age?:number };

let personTypedOptional: PersonTypeOptional = {
  name: 'name',
  hairColour: 'black',
  age: 123,
};

let personTypedOptional2: PersonTypeOptional = {
  name: 'name',
};

let optionalHairColour: string = personTypedOptional2.hairColour || "Bald";

console.log(optionalHairColour);