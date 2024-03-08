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