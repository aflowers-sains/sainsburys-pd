let x1 = 1;
var x2 = 2;
{
  // let is scoped
  let x1 = 3;
  var x2 = 33;
  console.log(x1);
  console.log(x2);
}

console.log(x1);
console.log(x2);