import React from 'react'

interface Props extends Partial<React.DetailedHTMLProps<React.HTMLAttributes<HTMLDivElement>, HTMLDivElement>> {
	className?: string
	style?: React.CSSProperties
	column?: boolean
	full?: boolean
	center?: boolean
}

const View: React.FC<Props> = ({className, style = {}, full, column, center, children, ...otherProps}) => {
	const styled = {} as React.CSSProperties
	if (full) {
		styled.flex = 1
	}
	if (column) {
		styled.flexDirection = 'column'
		styled.display = 'flex'
	}
	if (center) {
		styled.display = 'flex'
		styled.justifyContent = 'center'
		styled.alignItems = 'center'
	}
	return (
		<div className={className} style={{...styled, ...style}} {...otherProps}>
			{children}
		</div>
	)
}

export default View
