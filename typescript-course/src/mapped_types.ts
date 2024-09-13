type Mapped = {
  readonly id: string,  // cannot be changed
  name: string,
  email: string,
  age: number,
};

// mapped with only certain fields
type MappedData = Pick<Mapped, "name" | "email" | "age">;

let newMappedData: MappedData = {
  name: 'n',
  email: 'xyzzy@sainsburys.co.uk',
  age: 123
};

let newMapped = {
  ...newMappedData,
  id: 'ab123',
};

// make all fields read only
type ConstantMapped = Readonly<Mapped>;

let constantMapped: ConstantMapped = {
  id: 'constant',
  name: 'c',
  email: 'xyzzy@sainsburys.co.uk',
  age: 123
}

// cannot do this as readonly
// user.id = '1234';

// make a sub class only require certain fields
type PartialMapped = Partial<Mapped>;

let partialMapped: PartialMapped = {
  id: '123',
}