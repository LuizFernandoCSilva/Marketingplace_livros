const express = require('express');
const axios = require('axios'); // Biblioteca para requisições HTTP
const stripe = require('stripe')('sk_test_51QPp72K9FEOrCL2kZyup0DmAwmI0eTxTRke97w3s8C3x9lLLoESFpaqjotn7F4pBnZTGOzQNsiyeOiUypMPzcxUU00CyRqFNp7'); // Chave da API Stripe
const app = express();
const PORT = 3000;

const API_KEY = 'AIzaSyCafTuUYUgEYMDFkQiuB3HL4FB0DVJsACE';

// Middleware para lidar com dados JSON
app.use(express.json());

// Endpoint para buscar livros
app.get('/books', async (req, res) => {
  const query = req.query.q; 
  
  if (!query) {
    return res.status(400).send({ error: 'O parâmetro "q" é obrigatório para busca.' });
  }

  try {
    // Fazendo a requisição para a Google Books API
    const response = await axios.get('https://www.googleapis.com/books/v1/volumes', {
      params: {
        q: query,
        key: API_KEY,
      },
    });

    // Retornando os dados ao cliente
    res.json(response.data);
  } catch (error) {
    console.error('Erro ao acessar a Google Books API:', error.message);
    res.status(500).send({ error: 'Erro ao buscar os dados dos livros.' });
  }
});

// Endpoint para criar o PaymentIntent (para Stripe)
app.post('/create-payment-intent', async (req, res) => {
  const { amount, currency = 'usd' } = req.body; // Recebe o valor e a moeda, padrão USD
  
  if (!amount) {
    return res.status(400).send({ error: 'O valor é obrigatório.' });
  }

  try {
    // Criando o PaymentIntent no Stripe
    const paymentIntent = await stripe.paymentIntents.create({
      amount,        // O valor a ser cobrado em centavos
      currency,      // Moeda (USD por padrão)
      payment_method_types: ['card'],
    });

    // Retornando o client_secret para o frontend
    res.send({
      clientSecret: paymentIntent.client_secret,
    });
  } catch (error) {
    console.error('Erro ao criar o PaymentIntent:', error.message);
    res.status(500).send({ error: 'Erro ao processar o pagamento.' });
  }
});

// Inicia o servidor
app.listen(PORT, () => {
  console.log(`Servidor rodando em http://localhost:${PORT}`);
});
