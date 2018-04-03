import { Component } from './Component';

export class Order {
  id: number;
  name: string;
  orderDate: string;
  isComplete: boolean;
  components: Component[];
}
