class ProductModel {
  id: string;

  name: string;

  price: number;

  color: string;

  material?: string;

  size: string;

  gender: string;

  description?: string;

  stock: number;

  image?: string;

  subtype: string;

  constructor(id: string, name: string, price: number, color: string, material: string, size: string, gender: string, description: string, stock: number, image: string, subtype: string) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.color = color;
    this.material = material;
    this.size = size;
    this.gender = gender;
    this.description = description;
    this.stock = stock;
    this.image = image;
    this.subtype = subtype;

  }

}

export default ProductModel;