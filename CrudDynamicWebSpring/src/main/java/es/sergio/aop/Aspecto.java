package es.sergio.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Aspecto {
	
	@Pointcut("execution(public * es.sergio.dao.*.*(..))")
	public void ejemplo() {
		
	}
	
	@Before("ejemplo()" )
	public void antesQueTodo() {
		System.out.println("---------------------------------------");
		System.out.println("ESTO LO MOSTRAMOS ANTES DE CADA METODO");
		System.out.println("---------------------------------------");

	}

}
