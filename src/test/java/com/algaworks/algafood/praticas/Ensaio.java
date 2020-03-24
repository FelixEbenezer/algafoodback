package com.algaworks.algafood.praticas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Ensaio {
	
	
	
	

	    public static void main(String[] args) {
// range(1,10) = 1,2,3,4,5,6,7,8,9
//x,y=1,2   x,y=3,4   x,y=5,6  x,y=7,8   x,y=9
//1+2=3       3+4=7    5,6=11    7+8=15    9= 9  total geral= 45
	    	
	    	int tot = 2*12*30*56*9;  //  Res= 362880
	    	System.out.println(tot);
	  //----------------------------------------------------------------------  	
	        IntStream.range(1, 10)
	        		//.reduce((x, y) -> x * y)   //  Res= 362880
	        		.reduce((x, y) -> x + y)   // Res= 45
	                .ifPresent(s -> System.out.println(s));
	 //----------------------------------------------------------------------      
	        IntStream.range(1, 10)
	        		//.reduce(Integer::sum)
	        		.reduce((n,y) -> Integer.sum(n, y)) // Res= 45
	                .ifPresent(s -> System.out.println(s));
	//----------------------------------------------------------------------        
	        IntStream.range(1, 10)
	        		//.reduce(MyUtil::add2Ints)
	        		.reduce((a,b) -> MyUtil.add2Ints(a, b))       // Res= 45
	                .ifPresent(s -> System.out.println(s));
	//----------------------------------------------------------------------        
	        IntStream.range(1, 3)
	        		 .reduce((a,b) -> a>b ? a : b )     //2
	        		 .ifPresent(System.out::println);
    //----------------------------------------------------------------------        
	      int nn =   IntStream.range(1, 3)
	        		.reduce(0, (a,b) -> a>b ? a : b );  // ici on mis 0 como valor inicial
	      System.out.println(nn);                      //porque o nosso retorno é para ser 
	                                   // armazenado num inteiro, e quando o retorno é uma
	                                  // forma primitva, não se pode usar ifPresent que retorna um optional
	     
//----------------------------------------------------------------------      
	      Produto p1 = new Produto(1L, "pao", 2000, 12.5);
	  	Produto p2 = new Produto(2L, "cao", 3000, 0.5);
	  	Produto p3 = new Produto(3L, "Caneta", 150, 20.0);
	  	
	  	final List<Produto> produtos = new ArrayList<>();
		
		produtos.add(p1);
		produtos.add(p2);
		produtos.add(p3);
		produtos.add(p1);
		
		for (Produto produto : produtos) {
			System.out.println(produto);			
		}
		
 //----------------------------------------------------------------------		

		produtos.stream()
		        .map(p -> p.getPreco())
		        .reduce((a,b) -> a>b ? a:b)
		        .ifPresent(System.out::println);  //Res = 20.0
//-----------		
	double bbb= 	produtos.stream()
        .mapToDouble(p -> p.getPreco())
        .reduce(0, (a,b) -> a>b ? a:b);
		
	System.out.println(bbb);
//---------------	
	double baba= 	produtos.stream()
	        .mapToDouble(p -> p.getAnoFabrico())
	        .reduce(0, (a,b) -> a+b);  // faz o somatorio de todos os anos 7150
			
		System.out.println(baba);
//-----------------------------
		        produtos.stream()
		        .map(p -> p.getAnoFabrico())
		        .reduce((a,b) -> Integer.sum(a, b))
		        .ifPresent(System.out::println);;  // faz o somatorio de todos os anos 7150.0

//---------------------------
	int aa =	        produtos.stream()
		        		//.map(p -> p.getAnoFabrico())
		        		.collect(Collectors.summingInt(p -> p.getAnoFabrico()));
	System.out.println(aa);  // faz o somatorio de todos os anos 7150

//-----------------------------------
	double be = produtos.stream()
				.collect(Collectors.summingDouble(p -> p.getPreco()));
	System.out.println(be);   // faz o somatorio de todos os precos
	
//===================================================================
	Item i1 = new Item("pao", 3, new BigDecimal(1.5), BigDecimal.ZERO);
	Item i2 = new Item("cao", 10, new BigDecimal(20.5), BigDecimal.ZERO);
	Item i3 = new Item("Caneta", 7, new BigDecimal(4.5), BigDecimal.ZERO);
	
	final List<Item> itens = new ArrayList<>();
	itens.add(i1);
	itens.add(i2);
	itens.add(i3); 
	itens.add(i3);
	
	itens.forEach(System.out::println);
	
	final List<Demande> demandes = new ArrayList<>();
	demandes.add(new Demande (new BigDecimal(1.5),BigDecimal.ZERO, BigDecimal.ZERO));
	demandes.add(new Demande (new BigDecimal(3.5),BigDecimal.ZERO, BigDecimal.ZERO));
	demandes.add(new Demande (new BigDecimal(4.5),BigDecimal.ZERO, BigDecimal.ZERO));
	
	demandes.forEach(System.out::println);
	
	Long num= demandes.stream()
		.collect(Collectors.counting());
	System.out.println(num);  // 3 e o numero dos elementos do array demandes
	
	Long numLenght= demandes.stream()
			.map(d -> d.getItens())
			.count();
		System.out.println(numLenght);
	
	
		Map<Integer, List<Produto>> bc = produtos.stream()
				.collect(Collectors.groupingBy(p -> p.getAnoFabrico()));
	//	forEach((anoFabrico, p) -> System.out.println(anoFabrico, p));

	
	/*demandes.stream()
			.map(p -> new Demande(
					p.getTaxaFrete(),
					p.getSubTotal(),
					p.getTaxaFrete(),
					p.getValorTotal()))
			.forEach(System.out::println);
	*/
	
	
	
	    }
	}

