// helpers/api-helper.js
class ApiHelper {
  constructor(request) {
    this.request = request;
    this.baseUrl = 'https://jsonplaceholder.typicode.com';  // <-- TAMBAHKAN DEFAULT BASE URL
  }

  // GET request
  async get(endpoint, params = {}) {
    // Gabungkan baseUrl dengan endpoint
    const url = new URL(endpoint, this.baseUrl);  // Sekarang this.baseUrl ada nilainya
    
    // Tambahkan query parameters
    Object.keys(params).forEach(key => 
      url.searchParams.append(key, params[key])
    );

    const response = await this.request.get(url.toString());
    let responseBody;

    try {
      responseBody = await response.json();
    } catch (error) {
      responseBody = await response.text();
    }

    return {
      status: response.status(),
      body: responseBody,
      headers: response.headers()
    };
  }

  // POST request
  async post(endpoint, data = {}) {
    const url = new URL(endpoint, this.baseUrl);
    const response = await this.request.post(url.toString(), { 
      data: data,
      headers: {
        'Content-Type': 'application/json'
      }
    });
    
    let responseBody;
    try {
      responseBody = await response.json();
    } catch (error) {
      responseBody = await response.text();
    }

    return {
      status: response.status(),
      body: responseBody,
      headers: response.headers()
    };
  }

  // PUT request
  async put(endpoint, data = {}) {
    const url = new URL(endpoint, this.baseUrl);
    const response = await this.request.put(url.toString(), { 
      data: data,
      headers: {
        'Content-Type': 'application/json'
      }
    });
    
    let responseBody;
    try {
      responseBody = await response.json();
    } catch (error) {
      responseBody = await response.text();
    }

    return {
      status: response.status(),
      body: responseBody,
      headers: response.headers()
    };
  }

  // DELETE request
  async delete(endpoint) {
    const url = new URL(endpoint, this.baseUrl);
    const response = await this.request.delete(url.toString());
    return {
      status: response.status(),
      headers: response.headers()
    };
  }

  // PATCH request
  async patch(endpoint, data = {}) {
    const url = new URL(endpoint, this.baseUrl);
    const response = await this.request.patch(url.toString(), { 
      data: data,
      headers: {
        'Content-Type': 'application/json'
      }
    });
    
    let responseBody;
    try {
      responseBody = await response.json();
    } catch (error) {
      responseBody = await response.text();
    }

    return {
      status: response.status(),
      body: responseBody,
      headers: response.headers()
    };
  }
}

module.exports = ApiHelper;