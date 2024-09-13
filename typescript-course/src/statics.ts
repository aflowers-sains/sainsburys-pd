class MyStaticClass {
  private static y = 1;
  static x = 0;
  static printX() {
    console.log(MyStaticClass.x);
  }
}
console.log(MyStaticClass.x);
MyStaticClass.printX();