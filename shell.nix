{ pkgs ? import <nixpkgs> {} }:

pkgs.mkShell {
  name = "jhotdraw-dev";

  buildInputs = with pkgs; [
    # Java Development Kit (JDK 21 with full GUI support)
    jdk21
    
    # Build tool
    maven
    
    # GUI/X11 dependencies for Swing applications
    xorg.libX11
    xorg.libXext
    xorg.libXrender
    xorg.libXtst
    xorg.libXi
    fontconfig
    freetype
    
    # Optional: useful dev tools
    git
  ];

  # Set JAVA_HOME
  JAVA_HOME = "${pkgs.jdk21}";

  # Ensure AWT/Swing can find the display
  shellHook = ''
    export _JAVA_AWT_WM_NONREPARENTING=1
    
    echo "=========================================="
    echo "JHotDraw Development Environment"
    echo "=========================================="
    echo "Java: $(java -version 2>&1 | head -1)"
    echo "Maven: $(mvn -version 2>&1 | head -1)"
    echo ""
    echo "Commands:"
    echo "  mvn clean compile          - Build the project"
    echo "  mvn test                   - Run tests"
    echo "  mvn exec:java -pl jhotdraw-samples/jhotdraw-samples-misc"
    echo "                             - Run SVG sample app"
    echo "=========================================="
  '';
}
