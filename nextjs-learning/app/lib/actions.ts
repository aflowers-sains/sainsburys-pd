'use server';

import { z } from 'zod';
import { revalidatePath } from 'next/cache';
import { redirect } from 'next/navigation';
import { fetchInvoiceById } from './data';
import { InvoicesTable } from './definitions';

const {
    invoices
  } = require('./placeholder-data.js');

const FormSchema = z.object({
  id: z.string(),
  customerId: z.string(),
  amount: z.coerce.number(),
  status: z.enum(['pending', 'paid']),
  date: z.string(),
});
 
const CreateInvoice = FormSchema.omit({ id: true, date: true });
const UpdateInvoice = FormSchema.omit({ id: true, date: true });

export async function createInvoice(formData: FormData) {
    const { customerId, amount, status } = CreateInvoice.parse({
        customerId: formData.get('customerId'),
        amount: formData.get('amount'),
        status: formData.get('status'),
      });
      
      const amountInCents = amount * 100;
      const date = new Date().toISOString().split('T')[0];

      invoices.push({customer_id: customerId, amount: amountInCents, status: status, date: date});

      revalidatePath('/dashboard/invoices');
      redirect('/dashboard/invoices');
}

export async function updateInvoice(id: string, formData: FormData) {
    const { customerId, amount, status } = UpdateInvoice.parse({
      customerId: formData.get('customerId'),
      amount: formData.get('amount'),
      status: formData.get('status'),
    });
   
    const amountInCents = amount * 100;

    //@ts-ignore
    const invoice: InvoicesTable = await fetchInvoiceById(id);
    invoice.customer_id = customerId;
    invoice.amount = amountInCents;
    invoice.status = status;

    revalidatePath('/dashboard/invoices');
    redirect('/dashboard/invoices');
  }