    DIRETORIO_SCRIPT=$(dirname "$0")
    DIRETORIO_PROJETOS="$DIRETORIO_SCRIPT"

    if [ ! -d "$DIRETORIO_PROJETOS" ]; then
    echo "Diretório de projetos não encontrado."
    exit 1
    fi

    for projeto in "$DIRETORIO_PROJETOS"/*; do
    if [ -d "$projeto" ] && [ "$(basename "$projeto")" != "db" ]; then
        echo "Acessando o projeto: $projeto"
        cd "$projeto" || exit
        mvn clean package -DskipTests
        echo "Concluído o build do projeto: $projeto"
        echo ""
    fi
    done
    cd ..
    echo "Iniciando os containers docker-compose de Infra"
    docker-compose -f docker-compose-infra.yml up -d

    sleep 30
    
    echo "Iniciando os containers docker-compose dos serviços"
    docker-compose -f docker-compose-servicos.yml up -d