interface PaymentMethod {
  processPayment(): void;
}

class CreditCardPayment implements PaymentMethod {
  processPayment(): void {
    console.log('Collecting the users credit card details for payment');
  }
}

class PaypalPaymentMethod implements PaymentMethod {
  processPayment(): void {
    console.log('Collecting the users paypal details for payment');
  }
}

class GoogleWalletPaymentMethod implements PaymentMethod {
  processPayment(): void {
    console.log('Collecting the users google wallet details for payment');
  }
}

class CheckoutFlow {
  checkout(selectedPayment: 'credit-card' | 'paypal' | 'google-pay') {
    let paymentMethod: PaymentMethod = selectedPayment === 'credit-card'
      ? new CreditCardPayment()
      : selectedPayment === 'paypal'
        ? new PaypalPaymentMethod()
        : new GoogleWalletPaymentMethod()

    paymentMethod.processPayment();
  }
}

let checkoutFlow: CheckoutFlow = new CheckoutFlow();

checkoutFlow.checkout('paypal');
checkoutFlow.checkout('credit-card');
// checkoutFlow.checkout('??');  // doesn't even compile