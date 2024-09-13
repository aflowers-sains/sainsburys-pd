interface ToBeIndexed {
  make: string,
  model: string | number,
  numberOfMiles: number;
}

let car: ToBeIndexed = {
  make: 'Ford',
  model: 'Escort',
  numberOfMiles: 125000
}

// type matches indexed fields type from ToBeIndexed
let indexedName: ToBeIndexed['model'] = car.model;

type IndexedModel = ToBeIndexed['model'];

let indexedName2: IndexedModel = car.model;

interface ToBeIndexedOptionalModel {
  make: string,
  model?: string | number,
  numberOfMiles: number;
}

type ToBeIndexedOptionalModelName = ToBeIndexedOptionalModel['model'] | ToBeIndexedOptionalModel['make'];

let indexedNameOptional: ToBeIndexedOptionalModelName = car.model || car.make;