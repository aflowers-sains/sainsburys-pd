const const_x = 1;
let var_y = 2;

// can doa compile time override, but const is still a javascript runtime thing
// @ts-ignore
const_x = 2;

var_y = 1;

console.log(const_x);