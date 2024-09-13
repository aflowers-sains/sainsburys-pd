interface Command {
  execute(): void;
  undo(): void;
}

class Counter {
  private value: number = 0;

  public setValue(newValue: number) {
    this.value = newValue;
  }

  public getValue() {
    return this.value;
  }
}

class IncrementCommand implements Command {
  private counter: Counter;

  constructor(counter: Counter) {
    this.counter = counter;
  }

  execute(): void {
    this.counter.setValue(this.counter.getValue() + 1);
  }

  undo(): void {
    this.counter.setValue(this.counter.getValue() - 1);
  }
}

let counter: Counter = new Counter();
let command: IncrementCommand = new IncrementCommand(counter);

console.log(`Current Value ${counter.getValue()}`);
command.execute();
command.execute();
command.execute();
console.log(`Current Value after many executes ${counter.getValue()}`);
command.undo();
console.log(`Current Value after undo ${counter.getValue()}`);

