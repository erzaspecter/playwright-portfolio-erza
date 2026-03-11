// helpers/api-helper.js
class ApiHelper {
  constructor(request) {
    this.request = request;
  }

  // GET request
  async get(endpoint, params = {}) {
    const response = await this.request.get(endpoint, {
      params: params
    });
    return {
      status: response.status(),
      body: await response.json(),
      headers: response.headers()
    };
  }

  // POST request
  async post(endpoint, data = {}) {
    const response = await this.request.post(endpoint, {
      data: data
    });
    return {
      status: response.status(),
      body: await response.json(),
      headers: response.headers()
    };
  }

  // PUT request
  async put(endpoint, data = {}) {
    const response = await this.request.put(endpoint, {
      data: data
    });
    return {
      status: response.status(),
      body: await response.json()
    };
  }

  // DELETE request
  async delete(endpoint) {
    const response = await this.request.delete(endpoint);
    return {
      status: response.status()
    };
  }

  // PATCH request
  async patch(endpoint, data = {}) {
    const response = await this.request.patch(endpoint, {
      data: data
    });
    return {
      status: response.status(),
      body: await response.json()
    };
  }
}

module.exports = ApiHelper;