const stripe = require('stripe')(process.env.STRIPE_SECRET_KEY);

async function createPaymentIntent(amount, currency) {
    return await stripe.paymentIntents.create({
        amount,
        currency,
        payment_method_types: ['card'],
    });
}

module.exports = { createPaymentIntent };
