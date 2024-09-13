function combine<T1 extends string | number, T2 extends string | number>(a: T1, b: T2): CombinationResultStringOrNumber<T1, T2> {
  if(typeof a === 'number' && typeof b === 'number') {
    return (a + b) as CombinationResultStringOrNumber<T1, T2>;
  } else {
    return (a as string + b as string) as CombinationResultStringOrNumber<T1, T2>;
  }
}

type CombinationResultStringOrNumber<T1 extends string | number, T2 extends string | number> = 
  T1 extends number ?
  T2 extends number ? number :
    string :
    string;

let result = combine(10, 'Hello');

// result will be a string as the conditional type has defined that so the following works

function takesString(x: string) {
  console.log(x);
}

takesString(result);