# PI - Projeto Integrador Desktop Senac

## Controle de Consultório

A proposta do projeto é aumentar a agilidade e funcionalidade na organização e consulta de dados importantes pelo usuário.<br>
Foi utilizado o sistema DAO (Data Access Object) para comunicação entre o banco de dados e a API.<br>

Um mês e meio de desenvolvimento (junho/julho de 2024).
<br>

<table>
  <tr>
    <th>Linguagens e ferramentas utilizadas</th>
    <td>
      <img alt="Static Badge" height="24em" src="https://img.shields.io/badge/Java-black">
      <img alt="Static Badge" height="24em" src="https://img.shields.io/badge/Java%20Swing-black">
      <img alt="Static Badge" height="24em" src="https://img.shields.io/badge/SQL-black">
      <img alt="Static Badge" height="24em" src="https://img.shields.io/badge/MySQL-black?logo=mysql&labelColor=white">
      <img alt="Static Badge" height="24em" src="https://img.shields.io/badge/Eclipse%20IDE-black?logo=eclipseide&logoColor=%232C2255">
    </td>
  </tr>
</table>

<br>

## Principais funcionalidades
  <tr>
    <td>
        <ul>
            <li>Cadastrar, listar, deletar e alterar pacientes, tratamentos e consultas;</li>
            <li>Paciente pode ter opção de possuir responsável ou não no cadastro.</li>
            <li>Conferir informações adicionais do paciente listado, podendo ser endereço ou informações do responsável do mesmo;</li>
            <li>Mostrar consultas agendadas ou passadas;</li>
            <li>Marcar/Desmarcar comparecimento do paciente referente à consulta;</li>
        </ul>
    </td>
</tr>

<br>
    
## Implementações Futuras
- Desenvolver um controle de estoque de materiais junto do programa de controle do consultório.
- O tratamento terá uma nova opção para registrar produtos utilizados no tratamento e sua quantia.
- Quando a consulta for passada na agenda e for marcado comparecido, dependendo do tratamento realizado é automaticamente
removido a quantia do produto do estoque.
- O usuário poderá controlar o estoque manualmente também.

<br>

## Notas

Enfrentamos algumas dificuldades ao associar os IDs corretos em listas dinâmicas ao buscar dados do banco de dados. Para resolver isso, utilizamos um Map para armazenar e recuperar os IDs, garantindo a integridade dos dados, mesmo com as atualizações frequentes nas listas.

Além disso, priorizamos o desenvolvimento das funcionalidades do programa em vez do design, seguindo a recomendação do professor de utilizar Java Swing para a implementação da interface gráfica. Essa escolha foi feita para atender aos requisitos do curso, que era centrado apenas em Java, e garantir que o foco estivesse no aspecto funcional do sistema.

<br>

## Autor
| [<img loading="lazy" src="https://avatars.githubusercontent.com/u/168394448?v=4" width=115><br><sub>Lorenzo Rover</sub>](https://github.com/lorenzorover) | [<img loading="lazy" src="https://avatars.githubusercontent.com/u/43870620?v=4" width=115><br><sub>Thiago Souza</sub>](https://github.com/wzthiago)|
| :---: | :---: |
<br>

Release date:<br>
jul/2024
