interface Observer<T> {
  update(value: T): void;
}

class Subject {
  private observers: Observer<string>[] = [];
  private value: string = '';

  public addObserver(o: Observer<string> ) {
    this.observers.push(o);
  }

  public setValue(newValue: string) {
    this.value = newValue;

    this.observers.forEach(observer => observer.update(newValue));
  }
}

class LogObserver implements Observer<string> {
  update(value: string): void {
    console.log(`Changed value to ${value}`);
  }
}

let observer = new LogObserver();
let observer2 = new LogObserver();
let observer3 = new LogObserver();
let subject = new Subject();
subject.addObserver(observer);
subject.addObserver(observer2);
subject.addObserver(observer3);
subject.setValue('Design Patterns');