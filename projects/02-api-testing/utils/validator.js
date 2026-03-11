// utils/validator.js
const Joi = require('joi');

class Validator {
  static validateUser(user) {
    const schema = Joi.object({
      id: Joi.number().required(),
      name: Joi.string().required(),
      username: Joi.string(),
      email: Joi.string().email().required(),
      address: Joi.object({
        street: Joi.string(),
        suite: Joi.string(),
        city: Joi.string(),
        zipcode: Joi.string()
      }),
      phone: Joi.string(),
      website: Joi.string().uri()
    });

    return schema.validate(user);
  }

  static validatePost(post) {
    const schema = Joi.object({
      userId: Joi.number().required(),
      id: Joi.number().required(),
      title: Joi.string().required(),
      body: Joi.string().required()
    });

    return schema.validate(post);
  }
}

module.exports = Validator;