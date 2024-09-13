let iAmAString = 'I am a string';

if (typeof iAmAString === 'string') {
  console.log('it is a string');
}

let p3 = {
  name: 'n',
  age: 123,
};

// make a type based on an object and its fields
type P3Based = typeof p3;

type P3BasedKey = keyof P3Based;

function getPropertyFromP3Based(p: P3Based, key: P3BasedKey) {
  return p[key];
}

// this is not type safe - key can be anything
function getPropertyFromP3BasedViaString(p: P3Based, key: string) {
  return p[key];
}
