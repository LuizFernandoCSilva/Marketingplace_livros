const express = require('express');
const axios = require('axios'); // Biblioteca para requisições HTTP
const app = express();
const PORT = 3000;

const API_KEY = 'AIzaSyCafTuUYUgEYMDFkQiuB3HL4FB0DVJsACE';

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

// Inicia o servidor
app.listen(PORT, () => {
  console.log(`Servidor rodando em http://localhost:${PORT}`);
});
