# OrderBookJava
![image](https://github.com/user-attachments/assets/fa3af736-793d-4bb1-bab4-2dbe19c5eac5)

**OrderBookJava** is a terminal-based implementation of an electronic order book for trading systems, written in Java. It provides a robust and efficient engine for processing buy and sell orders.

## Key Features
- **Types of Orders**:
  - Limit Order
  - Market Order
    
- **Order Management**:
  - Add new buy or sell orders.
  - Execute trades by matching available orders.
  
- **Matching Logic**:
  - Automates the matching of compatible buy and sell orders based on price-time priority.
  - Ensures trades are executed efficiently and accurately.

- **Optimized Performance**:
  - The implementation uses a combination of data structures to achieve optimal performance for core operations.
  - **O(1)** for order lookup, modification, or cancellation by `orderId` (using a `HashMap`).
  - **O(log P)** for adding an order or canceling an order that removes a price level, where **P** is the number of unique price levels (using a `TreeMap`).

## Project Structure

- **Core Classes**:
  - `OrderBook`: Manages the main operations for the order book, including adding, removing, and matching orders.
  - `Order`: Represents a single order with details like order type (buy/sell), price, and quantity.
  - `OrderBookApp`: The starting point of the program.

- **Flow**:
  1. Orders are added to the system and placed into price-sorted queues.
  2. The matching engine identifies compatible buy and sell orders at the best prices.
  3. Matched orders are executed, and the corresponding quantities are removed from the book.

## Getting Started

### Prerequisites

- **Java Development Kit (JDK)**: Version 11 or higher.
- **Maven**: For dependency management and building the project.

### Setup

1.  Clone the repository:
    ```bash
    git clone https://github.com/pran9v/OrderBookJava.git
    cd OrderBookJava
    ```

2.  Build the project using Maven:
    ```bash
    mvn clean install
    ```

3.  Run the application:
    ```bash
    java -jar target/OrderBookJava.jar
    ```
    
### Usage

1.  **Add orders**:
    - Provide order details (side, price, quantity) via the terminal input.
2.  **View matched orders**:
    - Orders are automatically matched and executed when the bid and ask prices cross.
3.  **Monitor the order book**:
    - Unmatched orders remain in the system, waiting for a compatible order.
